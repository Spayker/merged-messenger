package com.spandr.meme.core.activity.authorization.logic;

import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public interface ActionAuthorizer {

    String IS_REGISTER_SCENARIO_RUNNING = "isRegisterScenarioRunning";

    void signUp() throws AppFireBaseAuthException;

    void signIn(String login, String password) throws AppFireBaseAuthException;

}
