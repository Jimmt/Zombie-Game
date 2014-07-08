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

package com.jumpbuttonstudios.zombiegame.ai.state;

import com.jumpbuttonstudios.zombiegame.ai.StateMachine;

/**
 * The State interface can be implemented into your custom states and passed to
 * a {@link StateMachine} where it will be handled. When creating your state it
 * is probably better to have only one instance of it, this saves creating it
 * every time a state changes and having the GC kick in, especially if you have
 * plenty of objects moving in and out of states. But if your state has fields,
 * I recommend not to do that.
 * <p>
 * 
 * To put into perspective what a State could be, you could implement several
 * for your in-game characters or "NPCs", this is why it is under the package
 * ai.<br>
 * These could be Idle, Move, Attack, Flee. You would setup the state in the
 * enter method and then from there the execute method will be called
 * continuously, updating the State and keeping your NPC Idle, Moving towards a
 * destination, Attacking or even Fleeing<br>
 * <p>
 * A common State is a "PathFinding" state, where you would have the enter
 * method setup the node map and then have the execute method run through your
 * open and close list, then exit the state and enter into a Move state,
 * utilising the path.
 * 
 * @author Stephen Gibson
 */
public interface State {

	/**
	 * Called when the {@link StateMachine} changes to this state
	 * 
	 * @param object
	 */
	void enter(Object object);

	/**
	 * Called in the {@link StateMachine} update method, the actual state should
	 * be updated in here
	 * 
	 * @param object
	 */
	void execute(Object object);

	/**
	 * Called when the {@link StateMachine} changes to another state
	 * 
	 * @param object
	 */
	void exit(Object object);

}
