flappybalt-GDX
==============

.. image:: http://tube42.github.io/flappybalt/img/flappy.png


This is my Java + libGDX port of FlappyBalt by Adam Atomics. The original game was written in AS3 + Flixel (`Source <https://github.com/AdamAtomic/Flappybalt>`_, `Game <http://adamatomic.com/flappybalt/>`_).
Note that if you are thinking about porting your flixel project to mobile platforms, you might want to look at `flixel-gdx <https://github.com/flixel-gdx/flixel-gdx>`_ instead (haven't tried it myself).




I did this project to compare Flixels way of doing things vs using barebone libgdx (i.e. not even scene2d or the tween libs). I have also done this for löve2d, see `this repo <https://github.com/tube42/flappybalt-love2d>`_. All in all, a very interesting exercise in both cases.



Instructions
-------------

On ubuntu/debian:
::

    ant setup  # download libgdx and extract the needed .JAR files
    ant run    # compile and runs the code
