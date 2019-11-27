package com.guli.statistics.client;

import com.guli.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "guli-ucenter")
public interface UcenterRemoteService {
    @GetMapping("/ucenter/member/registerCount/{date}")
    public Result getRegisterCount(@PathVariable("date") String date);
}
