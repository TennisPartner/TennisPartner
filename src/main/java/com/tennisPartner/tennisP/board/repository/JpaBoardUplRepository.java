package com.tennisPartner.tennisP.board.repository;

import com.tennisPartner.tennisP.board.domain.UplBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardUplRepository extends JpaRepository<UplBoard, Long> {

}
