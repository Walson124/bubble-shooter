# bubble shooter
 Bubble Shooter Game Remake
    - Just for fun, made from scratch
    - did bubble shooter in 10th grade high school, but i've been meaning to redo it because the original was buggy
    - everything is from scratch because old school account with all the files got deleted

 To future me:
    - You completed everything up to shooting and popping 2/24/22 - 2/25/22, spent around 14 hours

 Code Info:
    Driver:
       - Main
       - generateBubbles
       - frameSetup
       - panelSetup
       - buttonSetup

    MainPanel:
       - Public variables
       - Private variables
       - Initializers
       - Getters/setters
       - paint (paintComponent, Bubbles)
       - addButtons
       - draw (About, Arrow, Borders, Bubble, GameOver, Help, Misc)
       - setup (About, Help)
       - startRefresh

    Projectile:
       - Public variables
       - Private variables
       - Initializers
       - Getters/setters
       - calculate (Angle/Distance)
       - check (Bounds/Contact)
       - find (MinDistance, Safe, SameColor)
       - gameOver
       - move
       - penalty
       - popLoose
       - resetProjectile
       - shoot