package com.automafia.automafia.Round;

import org.springframework.stereotype.Service;

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
        Round rnd = new Round(++roundNumber);
        roundRepository.save(rnd);
        return rnd;
    }

    public Round setRoundAsFinished(long id) {
        Optional<Round> round = getRoundById(id);
        round.get().setRoundFinished(true);
        roundRepository.save(round.get());
        return round.get();
    }

    public Round endLastRound(long id) {
        Optional<Round> endedRound = getRoundById(id);
        if (!endedRound.isPresent()) {
            throw new IllegalStateException("ended round not found");
        }
        setRoundAsFinished(id);
        return endedRound.get();
    }
}
