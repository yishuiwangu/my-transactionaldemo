package com.yswg.mytransactionaldemo.dao;

import com.yswg.mytransactionaldemo.entity.Notable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotableDao extends JpaRepository<Notable,Long>{
}
