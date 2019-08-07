package com.oa.auth.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author kk
 * @date 2017/12/10
 */
public class Query<T>{
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String ORDER_BY_FIELD = "orderByField";
    private static final String IS_ASC = "isAsc";

    private Integer page;
    private Integer size;
    private Sort sort=null;


    public Query(Map<String, Object> params) {
        page = Integer.parseInt(params.getOrDefault(PAGE, 1).toString());
        size = Integer.parseInt(params.getOrDefault(LIMIT, 10).toString());
        String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
        if (!StringUtils.isEmpty(orderByField)) {
            sort = isAsc ? new Sort(Sort.Direction.ASC, orderByField) :
                    new Sort(Sort.Direction.DESC, orderByField);

        }


        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);

    }

    public Pageable getPageable(){
        return new PageRequest(page-1,size,sort);
    }
}
