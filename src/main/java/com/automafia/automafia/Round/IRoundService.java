package com.automafia.automafia.Round;

import java.util.Optional;

public interface IRoundService {
    Optional<Round> getRoundById(long id);
}
