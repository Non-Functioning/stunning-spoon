For this assignment, we created a model for a SimpleAnimation. Besides the model class there are
other classes that make up the parts of the animation. These classes are the AnimatedShape class,
the Animations class, the Position2D class, and the RBG class.

The Position2D class and the RBG class represents a 2D position and a red-green-blue value
respectively. The AnimatedShape class represents a shape with variables that hold characteristics
about the shape such as the shape type(an enumeration that can be expanded upon later), the
color(RBG class), the position(Position2D class), the shape's name, the shape's reference
point(an enumeration), the shape's size(a List of Double values), and the time of the shape's
appearance and disappearance. An AnimatedShape interface was added to group together the public
methods to get information from the shape class

The Animations class represents an animation that can be done to a shape. This class is extended
by 5 other classes that represent the specific type of animation. These include MoveShape,
ChangeShapeColor, ChangeShapeSize, shapeAppears, and shapeDisappears. More animations can be added
the future by adding another class that extends the Animations class. For this assignment, a
StillShape animation was added. This animation is used to keep track of shapes when no animation
is being performed on them. Also an Animations interface was added to group together the public
methods to get and set information from the animation class.

Next the SimpleAnimation interface contains methods that allow creating new shapes, performing
animations, printing the status of the animations and shapes, and removing shapes and animations.
Also, for this assignment, methods were added to calculate the position, color, or size of a shape
at a given time. The SimpleAnimation class implements these.

In the SimpleAnimation class, there are 3 Lists that contain and upkeep the status of the
animation. The shapes List contains all the shapes in order of appearance time. The animations
List contains all Animations in order of start time and lastly, the timeline List contains Lists
of Animations. Each List in the timeline represents one point in time. The List at each point
contains all animations that should occur at that point in time. This way if an Animation occurs
from t=10 to t=20, that Animation should be in all Lists in the timeline from index 10 to index 20.
 All methods in this class modify and upkeep these Lists to represent the animation. Additionally,
 for this assignment, update position, color, or size methods were added to update shape values in
 the animations and timeline Lists whenever an animation is added to the model.

Changes made to the model in this assignment were related to keeping track of shapes at times
when an animation was not being performed on that shape. This was done so that the view can access
and draw a shape correctly at any point in time.

Three views were added to this project to display the model in different ways. The first way in
the TextView. This view returns a String that is a list of shapes and animations and their
corresponding characteristics.

The second view is the VisualView. This view uses JFrame and Graphics classes to create a window
and draw shapes and their animations in that window. The shapes are drawn based on their
characteristics at a given time.

The last view is the SVGView. This view returns a String that correlates to an SVG file that can
be run in a browser to see the animation.

//MOST RECENT CHANGES FOR ASSIGNMENT 7
In assignment 7, a few changes were made to the model. These changes were done to add appear and
disappear animations to the animations List. This was done in order for the SVG view to easily
access these animations and fix a bug where the svg had incorrect overlapping shapes.

The next changes were made to the visual view. Changes were made to the visual view's
startAnimation method in order to simplify and shorten the code. These changes also fixed a bug in
the visual view where the shapes would blink when progressing through ticks.

Additionally, when adding the interactive view, the AbstractVisualView class was added. This class
creates the animation window and draws the animation on that window. The visual and interactive
views now extend off of this class. When creating the interactive view, functionality was added
for playing/pausing, changing the tempo, and looping the animation.