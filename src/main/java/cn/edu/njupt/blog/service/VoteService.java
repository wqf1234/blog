package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Vote;

public interface VoteService {
    /**
     * 根据id获取 Vote
     * @param id
     * @return
     */
    Vote getVoteById(Long id);
    /**
     * 删除Vote
     * @param id
     * @return
     */
    void removeVote(Long id);
}
