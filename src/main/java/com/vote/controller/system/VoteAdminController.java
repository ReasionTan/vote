package com.vote.controller.system;

import com.vote.basic.response.PageResponse;
import com.vote.basic.response.ResponseWrap;
import com.vote.service.VoteAdminService;
import com.vote.vo.req.system.RecordListReq;
import com.vote.vo.req.system.VoteCandidateReq;
import com.vote.vo.resp.system.CandidateListResp;
import com.vote.vo.resp.system.RecordListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "管理系统相关接口")
@RestController
@RequestMapping("/admin")
public class VoteAdminController {

    @Autowired
    private VoteAdminService voteAdminService;

    @ApiOperation(value = "添加选举")
    @PostMapping("/addVote")
    public ResponseWrap addVote(@ApiParam(value = "选举名称") @RequestParam String name){
        return voteAdminService.addVote(name.trim());
    }

    @ApiOperation(value = "开始/结束选举")
    @PostMapping("/updateVoteStatus")
    public ResponseWrap updateVoteStatus(@ApiParam(value = "选举id") @RequestParam Long voteId,
                                         @ApiParam(value = "选举状态：1：开始，2：结束") @RequestParam Integer status){
        return voteAdminService.updateVoteStatus(voteId, status);
    }

    @ApiOperation(value = "添加选举候选人")
    @PostMapping("/addCandidate")
    public ResponseWrap addCandidate(@Valid @RequestBody VoteCandidateReq voteCandidateReq){
        return voteAdminService.addCandidate(voteCandidateReq);
    }

    @ApiOperation(value = "查询投给候选人的用户列表")
    @PostMapping("/getRecordList")
    public ResponseWrap<PageResponse<RecordListResp>> getRecordList(@Valid @RequestBody RecordListReq recordListReq){
        return voteAdminService.getRecordList(recordListReq);
    }

    @ApiOperation(value = "查询选人列表及票数")
    @PostMapping("/getVoteResult")
    public ResponseWrap<List<CandidateListResp>> getVoteResult(@ApiParam(value = "选举id") @RequestParam Long voteId){
        return voteAdminService.getVoteResult(voteId);
    }
}
