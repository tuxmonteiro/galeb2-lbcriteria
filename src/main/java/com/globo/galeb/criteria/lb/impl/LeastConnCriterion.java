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

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.globo.galeb.criteria.ICriterion;
import com.globo.galeb.criteria.lb.IBackendComparable;


/**
 * Class LeastConnCriterion.
 *
 * @author See AUTHORS file.
 * @version 1.0.0, Nov 9, 2014.
 * @param <T> the generic type
 */
public class LeastConnCriterion<T extends Comparable<T>> implements ICriterion<T> {

    /** The map. */
    private Map<String, T> map;

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#given(java.util.Map)
     */
    @Override
    public ICriterion<T> given(final Map<String, T> map) {
        this.map = map;
        return this;
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#when(java.lang.Object)
     */
    @Override
    public ICriterion<T> when(final Object param) {
        return this;
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#thenGetResult()
     */
    @Override
    public T thenGetResult() {

        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return null;
        }

        Comparator<? super T> comparator = new Comparator<T>() {
            @Override
            public int compare(T be1, T be2) {
                if (be1 instanceof IBackendComparable && be2 instanceof IBackendComparable) {
                    return ((IBackendComparable) be1).getActiveConnections() - ((IBackendComparable) be2).getActiveConnections();
                }
                return 0;
            }
        };
        return Collections.min(map.values(), comparator);
    }


    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#action(com.globo.galeb.criteria.ICriterion.CriterionAction)
     */
    @Override
    public ICriterion<T> action(ICriterion.CriterionAction criterionAction) {
        return this;
    }

}
