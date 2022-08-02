package com.imall.thirdparty.modules.service.fegin;

import com.imall.thirdparty.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * AdminFeginClient
 *
 * @author zhangpengjun
 * @date 2022/7/26
 */
@Component
@FeignClient(name = "admin", url = "${third-party.fegin.admin}", path = "/inner/admin", configuration = FeignConfiguration.class)
public interface AdminFeginClient {

    /**
     * 查询
     */
    @GetMapping("/select")
    List<Object> selectAgent(@RequestParam Integer projectId);

    /**
     * 批量新增
     */
    @PostMapping("/insertBatch")
    Integer insertBatch(@RequestBody @Valid Object bo);
}
