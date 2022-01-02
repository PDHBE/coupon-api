package com.github.pdhbe.couponapi.controller;

import org.hamcrest.core.IsNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class BaseTest {
    static final DateTimeFormatter DB_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    private final String api;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseTest(String url) {
        this.api = url;
    }

    public JSONObject findUserById(String id) {
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                "SELECT * FROM users WHERE id = ?",
                id
        );
        return new JSONObject(resultMap);
    }

    public JSONObject findCouponGroupByCode(String code) {
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                "SELECT * FROM coupon_groups WHERE code = ?",
                code
        );
        return new JSONObject(resultMap);
    }

    static public JSONObject findAllHistories() {
        try {
            List<Map<String, Object>> resultMapList = new JdbcTemplate().queryForList(
                    "SELECT * FROM coupon_group_histories"
            );
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("histories",resultMapList);
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject findCouponByUserIdAndCode(String userId, String code) {
        try {
            Map<String, Object> resultMap = jdbcTemplate.queryForMap(
                    "SELECT * FROM coupons WHERE user_id = ? and code = ?",
                    userId,
                    code
            );
            return new JSONObject(resultMap);
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(JSONObject json, String prop) throws JSONException {
        return json.getString(prop.toUpperCase());
    }

    public int getInt(JSONObject json, String prop) throws JSONException {
        return json.getInt(prop.toUpperCase());
    }

    public String getDateString(JSONObject json, String prop) throws JSONException {
        return LocalDateTime
                .parse(getString(json, prop), DB_TIME_FORMAT)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public ResultActions failure(ResultActions res, HttpStatus status) throws Exception {
        return res.andExpect(status().is(status.value()))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(status.value())));
    }

    public ResultActions success(ResultActions res) throws Exception {
        return res.andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.error").value(IsNull.nullValue()));
    }

    public String apiUrl(String url) {
        return this.api + "/" + url;
    }

    public ResultActions reqGet(String url) throws Exception {
        return mockMvc.perform(get(apiUrl(url)))
                .andDo(print());
    }

    public ResultActions reqGet(String url, String userId) throws Exception {
        return mockMvc.perform(get(apiUrl(url)).header("X-USER-ID", userId))
                .andDo(print());
    }

    public ResultActions reqPost(String url) throws Exception {
        return mockMvc.perform(post(apiUrl(url)))
                .andDo(print());
    }

    public ResultActions reqPost(String url, JSONObject json) throws Exception {
        return mockMvc.perform(post(apiUrl(url))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print());
    }

    public ResultActions reqPost(String url, String userId) throws Exception {
        return mockMvc.perform(post(apiUrl(url)).header("X-USER-ID", userId))
                .andDo(print());
    }

    public ResultActions reqPost(String url, String userId, JSONObject json) throws Exception {
        return mockMvc.perform(post(apiUrl(url))
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print());
    }

    public ResultActions reqPut(String url) throws Exception {
        return mockMvc.perform(put(apiUrl(url)))
                .andDo(print());
    }

    public ResultActions reqPut(String url, JSONObject json) throws Exception {
        return mockMvc.perform(put(apiUrl(url))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print());
    }

    public ResultActions reqPut(String url, String userId) throws Exception {
        return mockMvc.perform(put(apiUrl(url)).header("X-USER-ID", userId))
                .andDo(print());
    }

    public ResultActions reqPut(String url, String userId, JSONObject json) throws Exception {
        return mockMvc.perform(put(apiUrl(url))
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andDo(print());
    }
}
