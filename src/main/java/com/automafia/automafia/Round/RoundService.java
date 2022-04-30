package com.automafia.automafia.Round;

import com.automafia.automafia.User.Roles.Role;
import com.automafia.automafia.User.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundService implements IRoundService {

    private RoundRepository roundRepository;
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public Optional<Round> getRoundById(long id) {
        return Optional.ofNullable(roundRepository.findById(id));
    }

    public Round createNewRound(int roundNumber) {
        Round round = new Round(++roundNumber);
        roundRepository.save(round);
        return round;
    }

    public Round save(Round round) {
        roundRepository.save(round);
        return round;
    }

//    public User getNextUserToGo(long roundId) {
//        Round round = roundRepository.findById(roundId);
//        List<User> roundList = round.getUserList();
////        TODO: GET AND REMOVE FROM HEAD USER
//        User nextUser = roundList.get(roundList.size());
//        roundList.remove(roundList.size());
//        Role userRole = nextUser.getRole();
//        return nextUser;
//    }

    public Round setRoundAsFinished(long id) {
        Optional<Round> round = getRoundById(id);
        round.get().setRoundFinished(true);
        roundRepository.save(round.get());
        return round.get();
    }

    public Round endLastRound(long id) {
        Optional<Round> endedRound = getRoundById(id);
        if (!endedRound.isPresent()) {
            throw new IllegalStateException("last round not found");
        }
        setRoundAsFinished(id);
        return endedRound.get();
    }
}
