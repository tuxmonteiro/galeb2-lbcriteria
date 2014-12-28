/*
 * Copyright (c) 2014 Globo.com - ATeam
 * All rights reserved.
 *
 * This source is subject to the Apache License, Version 2.0.
 * Please see the LICENSE file for more information.
 *
 * Authors: See AUTHORS file
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.globo.galeb.criteria.lb.impl;

import static com.globo.galeb.consistenthash.HashAlgorithm.HashType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.globo.galeb.consistenthash.ConsistentHash;
import com.globo.galeb.consistenthash.HashAlgorithm;
import com.globo.galeb.criteria.ICriterion;
import com.globo.galeb.criteria.lb.RequestProperties;


/**
 * Class IPHashCriterion.
 *
 * @author See AUTHORS file.
 * @version 1.0.0, Nov 9, 2014.
 * @param <T> the generic type
 */
public class IPHashCriterion<T> implements ICriterion<T> {

    /** The Constant DEFAULT_HASH_ALGORITHM. */
    public static final String DEFAULT_HASH_ALGORITHM     = SIP24.toString();

    /** The Constant HASH_ALGORITHM_KEY. */
    public static final String HASH_ALGORITHM_KEY         = "hashAlgorithm";

    /** The Constant DEFAULT_SOURCEIP. */
    private static final String DEFAULT_SOURCEIP          = "0.0.0.0";

    /** The Constant SOURCEIP_KEY. */
    private static final String SOURCEIP_KEY              = "sourceip";


    /** The collection. */
    private Collection<T>     collection     = new ArrayList<T>();

    /** The consistent hash. */
    private ConsistentHash<T> consistentHash = null;

    /** The hash type. */
    private String            hashType       = "";

    /** The source ip. */
    private String            sourceIp       = "";

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#given(java.util.Map)
     */
    @Override
    public ICriterion<T> given(final Map<String, T> map) {
        if (map!=null) {
            int lastCollectionSize = collection.size();
            this.collection = map.values();
            if (collection.size()!=lastCollectionSize) {
                consistentHash = null;
            }
        }
        return this;
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#when(java.lang.Object)
     */
    @Override
    public ICriterion<T> when(final Object param) {
        if (param instanceof RequestProperties) {
            RequestProperties requestProperties = (RequestProperties) param;
            String lastHashType = hashType;
            this.hashType = requestProperties.getString(HASH_ALGORITHM_KEY, DEFAULT_HASH_ALGORITHM);
            if ("".equals(hashType)) {
                this.hashType = DEFAULT_HASH_ALGORITHM;
            }
            if (!hashType.equals(lastHashType)) {
                consistentHash = null;
            }
            this.sourceIp = requestProperties.getString(SOURCEIP_KEY, DEFAULT_SOURCEIP);
        }
        return this;
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#thenGetResult()
     */
    @Override
    public T thenGetResult() {

        if (collection.isEmpty() || "".equals(sourceIp)) {
            return null;
        }

        int numberOfReplicas = 1;

        if (consistentHash == null) {
            consistentHash = new ConsistentHash<T>(new HashAlgorithm(hashType),
                                                    numberOfReplicas, collection);
        }

        return consistentHash.get(sourceIp);
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#action(com.globo.galeb.criteria.ICriterion.CriterionAction)
     */
    @Override
    public ICriterion<T> action(ICriterion.CriterionAction criterionAction) {
        switch (criterionAction) {
            case RESET_REQUIRED:
                consistentHash = null;
                break;

            default:
                break;
        }
        return this;
    }

}
