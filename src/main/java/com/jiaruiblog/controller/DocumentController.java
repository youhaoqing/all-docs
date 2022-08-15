package com.jiaruiblog.controller;

import com.jiaruiblog.entity.dto.DocumentDTO;
import com.jiaruiblog.entity.dto.RemoveObjectDTO;

import com.jiaruiblog.intercepter.SensitiveFilter;
import com.jiaruiblog.service.IFileService;
import com.jiaruiblog.utils.ApiResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @ClassName DocumentController
 * @Description 文档查询删除控制器
 * @Author luojiarui
 * @Date 2022/6/19 5:18 下午
 * @Version 1.0
 **/

@Api(tags = "文档模块")
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    IFileService iFileService;


    @ApiOperation(value = "2.1 查询文档的分页列表页", notes = "根据参数查询文档列表")
    @PostMapping(value = "/list")
    public ApiResult list(@RequestBody DocumentDTO documentDTO) throws IOException {
        if(StringUtils.hasText(documentDTO.getFilterWord())) {
            String filterWord = documentDTO.getFilterWord();
            //非法敏感词汇判断
            SensitiveFilter filter = SensitiveFilter.getInstance();
            int n = filter.CheckSensitiveWord( filterWord,0,1);
            //存在非法字符
            if( n > 0 ){
                log.info("这个人输入了非法字符--> {},不知道他到底要查什么~",filterWord);
            }
        }
        return iFileService.list(documentDTO);
    }

    @ApiOperation(value = "2.2 查询文档的详细信息", notes = "查询文档的详细信息")
    @GetMapping(value = "/detail")
    public ApiResult detail(@RequestParam(value = "docId") String id){
        return iFileService.detail(id);
    }

    @ApiOperation(value = "3.2 删除某个文档", notes = "删除某个文档")
    @DeleteMapping(value = "/auth/remove")
    public ApiResult remove(@RequestBody RemoveObjectDTO removeObjectDTO){
        return iFileService.remove(removeObjectDTO.getId());
    }

}
