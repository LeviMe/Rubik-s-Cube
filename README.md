## Rubik's Cube Java Implementation

This project is a Java implementation of the famous game Rubik's Cube. It was developed in 2017 and was shared on GitHub as a demonstration of my past work in 2019.

The game features a 3D interface that doesn't rely on an external game engine, but instead uses its own 3D functions to render the view using Java built-in swing framework.

The 3D rendering is done with points that are used to create triangles. These triangles are then rendered as 2D figures in a process called rasterization. This involves projecting the triangles onto a 2D plane and using techniques such as z-buffering to determine which pixels should be rendered in the final image, and perspective projection to account for the depth of each pixel.

The project follows a Model-View-Controller (MVC) pattern, with PanneauCube acting as the view and the controllers, and RubikCubeModel as the model.

### Game Instructions (Azerty Keyboard)
- Open a terminal and navigate to the bin folder.
- Start the game by running the command java JeuMain.
- The 6 faces of the cube can be rotated counterclockwise using the keys A, Z, E, Q, S, and D.
Adding the Shift key will rotate them clockwise.
-  The whole cube can be rotated around two axes of its local space using the arrow keys.

