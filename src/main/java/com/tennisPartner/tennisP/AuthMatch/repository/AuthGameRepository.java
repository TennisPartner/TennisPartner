package com.tennisPartner.tennisP.AuthMatch.repository;

import com.tennisPartner.tennisP.AuthMatch.domain.AuthGame;
import com.tennisPartner.tennisP.AuthMatch.domain.AuthMatch;
import com.tennisPartner.tennisP.AuthMatch.repository.selectColumn.GuestScore;
import com.tennisPartner.tennisP.AuthMatch.repository.selectColumn.HostScore;
import com.tennisPartner.tennisP.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthGameRepository extends JpaRepository<AuthGame,Long> {
    List<AuthGame> findByAuthMatch(AuthMatch authMatch);

    @Query(value= "select game.host1.userIdx as host1, game.host2.userIdx as host2, game.hostScore as hostScore, game.guestScore as guestScore from auth_game_tb game where game.authMatch = :#{#authMatch} and (game.host1 = :#{#host1} or game.host2 = :#{#host1})")
    List<HostScore> findHostScore(@Param(value = "authMatch") AuthMatch authMatch, @Param(value = "host1") User host1);

    @Query(value= "select game.guest1.userIdx as guest1, game.guest2.userIdx as guest2, game.hostScore as hostScore, game.guestScore as guestScore from auth_game_tb game where game.authMatch = :#{#authMatch} and (game.guest1 = :#{#guest1} or game.guest2 = :#{#guest1})")
    List<GuestScore> findGuestScore(@Param(value = "authMatch") AuthMatch authMatch, @Param(value = "guest1") User guest1);

    @Query(value= "select count(game) from auth_game_tb game where game.authMatch = :#{#authMatch} and (game.guest1 = :#{#userIdx} or game.guest2 = :#{#userIdx} or game.host1 = :#{#userIdx} or game.host2 = :#{#userIdx})")
    int findUserGameCount(@Param(value = "authMatch") AuthMatch authMatch, @Param(value = "userIdx") User user);

    void deleteByAuthMatch(AuthMatch authMatch);
}
