package cn.edu.njupt.blog.repository;

import cn.edu.njupt.blog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
