package com.tennisPartner.tennisP.unAuthMatch.service;

import com.tennisPartner.tennisP.unAuthMatch.dto.UnAuthMatchRequestDTO;
import java.util.List;

public interface UnAuthMatchService {

    List<List<Integer>> unAuthMatching(UnAuthMatchRequestDTO req);
}
