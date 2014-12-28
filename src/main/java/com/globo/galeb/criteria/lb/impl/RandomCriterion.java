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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.globo.galeb.criteria.ICriterion;


/**
 * Class RandomCriterion.
 *
 * @author See AUTHORS file.
 * @version 1.0.0, Nov 7, 2014.
 * @param <T> the generic type
 */
public class RandomCriterion<T> implements ICriterion<T> {

    /** The list index. */
    private final List<T> listIndex = new ArrayList<>();

    /** The collection. */
    private Map<String, T>   map;

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#given(java.util.Map)
     */
    @Override
    public ICriterion<T> given(final Map<String, T> map) {
        if (map!=null && listIndex.isEmpty()) {
            this.map = map;
            listIndex.addAll(map.values());
        } else {
            this.map = new HashMap<>();
        }
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
        if (listIndex.isEmpty()) {
            return null;
        }
        return listIndex.get(getIntRandom());
    }

    /**
     * Gets the int random.
     *
     * @return the int random
     */
    private int getIntRandom() {
        if (listIndex.isEmpty()) {
            return 0;
        }
        return (int) (Math.random() * (listIndex.size() - Float.MIN_VALUE));
    }

    /* (non-Javadoc)
     * @see com.globo.galeb.criteria.ICriterion#action(com.globo.galeb.criteria.ICriterion.CriterionAction)
     */
    @Override
    public ICriterion<T> action(ICriterion.CriterionAction criterionAction) {
        switch (criterionAction) {
            case RESET_REQUIRED:
                listIndex.clear();
                listIndex.addAll(map.values());
                break;

            default:
                break;
        }
        return this;
    }
}
