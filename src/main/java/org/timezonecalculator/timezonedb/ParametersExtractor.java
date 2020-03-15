package org.timezonecalculator.timezonedb;

import org.junit.platform.commons.util.StringUtils;
import org.timezonecalculator.ErrorResponse;
import ratpack.exec.Promise;
import ratpack.handling.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static ratpack.jackson.Jackson.json;

public class ParametersExtractor {
    private static final String CITY_QUERY_PARAM = "city";
    private static final String PLEASE_TYPE_IN_A_CITY_MESSAGE = "Please type in a city.";

    Promise<String> getCity(Context context) throws UnsupportedEncodingException {
        return Promise.value(URLEncoder.encode(context.getRequest().getQueryParams().get(CITY_QUERY_PARAM),
                "UTF-8"))
                .route(
                        city -> StringUtils.isBlank(city),
                        city -> context.render(json(new ErrorResponse(PLEASE_TYPE_IN_A_CITY_MESSAGE))));


    }


}
