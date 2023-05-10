package com.tennisPartner.tennisP.club.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.tennisPartner.tennisP.club.domain.Club;
import com.tennisPartner.tennisP.common.Exception.CustomException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.tennisPartner.tennisP.club.domain.QClub.club;
@Repository
public class ClubRepositorySupportImpl extends QuerydslRepositorySupport implements ClubRepositorySupport {

    @Autowired
    public ClubRepositorySupportImpl(){
        super(Club.class);
    }

    public Page<Club> findByCondition(String type, String condition, Pageable pageable){
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        switch(type){
            case "name":
                conditionBuilder.and(club.clubName.contains(condition));
                break;
            case "city":
                conditionBuilder.and(club.clubCity.contains(condition));
                break;
            case "county":
                conditionBuilder.and(club.clubCounty.contains(condition));
                break;
            case "" :
                break;
            default :
                throw new CustomException("검색 타입이 제대로 설정되지 않았습니다.", 205);
        }
        conditionBuilder.and(club.useYn.eq("Y"));

        JPQLQuery<Club> query =
                from(club)
                .select(club)
                .where(conditionBuilder);

        JPQLQuery<Club> q = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Club> results = q.fetchResults();
        List<Club> contents = results.getResults();

        long total = results.getTotal();
        return new PageImpl<>(contents, pageable, total);
    }
}
