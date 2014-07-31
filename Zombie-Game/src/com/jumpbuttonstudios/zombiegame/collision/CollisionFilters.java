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

package com.jumpbuttonstudios.zombiegame.collision;

/**
 * All the collision filters available to use
 * 
 * @author Stephen Gibson
 * @author Jimmt
 */
public class CollisionFilters {

	public static short GROUND = 0x0001;
	public static short ZOMBIE = 0x0002; 
	public static short PLAYER  = 0x0004;
	public static short BULLET = 0x0008;
	public static short BOUNDARY = 0x00016;
	public static short BODYPART = 0x00032;
	public static short DROP = 0x00064;
	public static short BRAIN = 0x00128;


}
