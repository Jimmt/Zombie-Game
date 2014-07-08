/**
 * Copyright 2014 Stephen Gibson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jumpbuttonstudios.zombiegame.ai;

import com.jumpbuttonstudios.zombiegame.GameUtilRuntimeException;
import com.jumpbuttonstudios.zombiegame.ai.state.State;

/**
 * A StateMachine that can be used in AI, using this you can have in-game
 * objects enter a {@link State}, perform some actions and then exit the
 * {@link State}. There are built in methods for you to revert to a previous
 * {@link State}, or to a default {@link State}<br>
 * 
 * @author Stephen Gibson
 */
public final class StateMachine {

	/**
	 * The object that owns this StateMachine, this will be passed in when the
	 * StateMachine is first created. Once the owner is set it makes no sense to
	 * change it
	 */
	final Object owner;

	/**
	 * Determines if the {@link State} that is currently set is locked, this
	 * prevents {@link State} from changing
	 */
	boolean isLocked;

	/**
	 * The default {@link State}, should be set to the Objects default
	 * {@link State} to avoid a stateless object
	 */
	State defaultState;

	/**
	 * The current {@link State} the object is in, this state will be executed
	 * in the StateMachines update method
	 */
	State currentState;

	/**
	 * The previous {@link State}, when the Objects {@link State} is changed,
	 * the one that was set before is automatically put in this variable
	 */
	State previousState;

	/**
	 * Construct a new StateMachine and give it an owner, each Object in your
	 * game should holds its own StateMachine
	 * 
	 * @param owner
	 *            an Object of some kind to be passed to the StateMachine
	 */
	public StateMachine(Object owner) {
		if (owner == null)
			throw new GameUtilRuntimeException(
					"Failed to create StateMachine, can not pass a null owner");
		this.owner = owner;
	}

	/**
	 * Construct a new StateMachine and give it an owner, each Object in your
	 * game should holds its own StateMachine<br>
	 * This constructor also allows you to specify a default state rather than
	 * using <code>setDefaultState()</code>
	 * 
	 * @param owner
	 *            an Object of some kind to be passed to the StateMachine
	 * @param defaultState
	 *            a state to which the owner can revert to if current or
	 *            previous is null
	 */
	public StateMachine(Object owner, State defaultState) {
		if (owner == null)
			throw new GameUtilRuntimeException(
					"Failed to create StateMachine, can not pass a null owner");
		if (defaultState == null)
			throw new GameUtilRuntimeException(
					"Failed to create StateMachine, can not pass a null default state");
		this.owner = owner;
		this.defaultState = defaultState;

	}

	/**
	 * Update the StateMachine, this will constantly run your code in the
	 * <code>currentState</code>s execute method
	 */
	public void update(Object owner) {
		if (currentState != null)
			currentState.execute(owner);

	}

	/**
	 * Change the {@link #currentState} in this StateMachine. This automatically
	 * sets up the {@link #previousState} as the {@link #currentState} and calls
	 * exit on the {@link #currentState} before changing it to the newState. It
	 * then calls enter on the new state
	 * 
	 * @param newState
	 */
	public void changeState(State newState) {
		/* If the StateMachine is locked, can't change State */
		if (isLocked)
			return;
		/*
		 * If the newState is the same as the currentState, no point in
		 * continuing
		 */
		if (newState == currentState)
			return;
		/* Set the previous state to the current state */
		previousState = currentState;
		/* If the current state is not null, we call its exit method */
		if (currentState != null)
			currentState.exit(owner);

		/* Set the current state to the new state */
		currentState = newState;

		/* If the current state is not null, enter it */
		if (currentState != null)
			currentState.enter(owner);
	}

	/**
	 * Changes from a current state to a state, executes it once and then
	 * continues to the new state given
	 * 
	 * @param nextState
	 * @param newStage
	 *            State the new state
	 */
	public void changeState(State nextState, State newState) {
		/* If the StateMachine is locked, can't change State */
		if (isLocked)
			return;
		/*
		 * If the newState is the same as the currentState, no point in
		 * continuing
		 */
		if (nextState == currentState)
			return;
		/* Set the previous state to the current state */
		previousState = currentState;
		/* If the current state is not null, we call its exit method */
		if (currentState != null)
			currentState.exit(owner);

		if (nextState == currentState)
			return;

		/* Run through the next state */
		nextState.enter(owner);
		nextState.execute(owner);
		nextState.exit(owner);

		/* Change to the new state */
		changeState(newState);
	}

	/**
	 * Reverts the <code>currentState</code> to the <code>previousState</code>.
	 * This will exit the <code>currentState</code>
	 */
	public void revertPrevious() {
		/* If the previous state is null, nothing can be reverted */
		if (previousState == null)
			return;
		/* If the current state is not null, we exit it */
		if (currentState != null)
			currentState.exit(owner);
		/* Set the current state to the previous state */
		currentState = previousState;

	}

	/**
	 * Set the default {@link State} for the StateMachine, this should be set to
	 * avoid problems when an object can not move to a new {@link State} and the
	 * previous {@link State} was never set.
	 */
	public void setDefaultState(State defaultState) {
		if (defaultState == null)
			throw new GameUtilRuntimeException(
					"Failed to set default state, the given state was null");
		if (this.defaultState == defaultState)
			return;
		if(currentState == null)
			this.currentState = defaultState;
		this.defaultState = defaultState;
	}

}
