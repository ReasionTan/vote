package com.vote.controller.app;

import com.vote.basic.constant.RedisConstant;
import com.vote.basic.response.ResponseWrap;
import com.vote.config.RedisDistributedLocks;
import com.vote.service.VoteService;
import com.vote.vo.req.app.RegisterReq;
import com.vote.vo.req.app.VoteReq;
import com.vote.vo.resp.system.CandidateListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "普通用户相关接口")
@RestController
@RequestMapping("/api")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @ApiOperation(value = "用户登记")
    @PostMapping("/register")
    @RedisDistributedLocks(lockKey = RedisConstant.USER_ID_CARD, onlyKey = "idCard", isLockPlatform = false)
    public ResponseWrap<Long> register(@Valid @RequestBody RegisterReq registerReq) {
        return voteService.register(registerReq.getIdCard(), registerReq.getEmail());
    }

    @ApiOperation(value = "用户投票")
    @PostMapping("/vote")
    @RedisDistributedLocks(lockKey = RedisConstant.USER_ID_CARD, onlyKey = "userId", isLockPlatform = true)
    public ResponseWrap<List<CandidateListResp>> vote(@Valid @RequestBody VoteReq voteReq) {
        return voteService.vote(voteReq);
    }
}
