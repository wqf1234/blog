package cn.edu.njupt.blog.service;

import cn.edu.njupt.blog.domain.Vote;
import cn.edu.njupt.blog.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    /* (non-Javadoc)
     * @see com.waylau.spring.boot.blog.service.VoteService#removeVote(java.lang.Long)
     */
    @Override
    @Transactional
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }
    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).get();
    }
}
