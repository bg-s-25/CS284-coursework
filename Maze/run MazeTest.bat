@echo off
color 3f
:start
javac -d classfiles MazeTest.java
echo.
echo -------------------------------
echo Press any key to RUN class file
echo -------------------------------
echo.
pause>nul
cd classfiles
java MazeTest