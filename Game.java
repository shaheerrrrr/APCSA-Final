/*Shaheer Khan
 * On My Honor as a Student, I have not given nor received aid on this lab
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel {
    private static final int SIZE = 5; // Increase the grid size to 5x5
    private static final int TILE_SIZE = 120; // Increase tile size to 120 pixels
    private static final int TILE_MARGIN = 10;
    private static final int WINNING_TILE = 2048;
    private int[][] tiles;
    private boolean won = false;
    private boolean lost = false;

    public Game() {
        setPreferredSize(new Dimension(SIZE * TILE_SIZE + (SIZE + 1) * TILE_MARGIN,
                SIZE * TILE_SIZE + (SIZE + 1) * TILE_MARGIN));
        setFocusable(true);
        resetGame();

        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (!won && !lost)
                {
                    switch (e.getKeyCode())
                    {
                        case KeyEvent.VK_LEFT:
                            moveLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            moveRight();
                            break;
                        case KeyEvent.VK_UP:
                            moveUp();
                            break;
                        case KeyEvent.VK_DOWN:
                            moveDown();
                            break;
                        case KeyEvent.VK_A:
                            moveLeft();
                            break;
                        case KeyEvent.VK_D:
                            moveRight();
                            break;
                        case KeyEvent.VK_W:
                            moveUp();
                            break;
                        case KeyEvent.VK_S:
                            moveDown();
                            break;
                    }
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_R)
                {
                    resetGame();
                    repaint();
                }
            }
        });
    }

    public void resetGame()
    {
        tiles = new int[SIZE][SIZE];
        addRandomTile();
        addRandomTile();
        won = false;
        lost = false;
    }

    public void addRandomTile()
    {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (tiles[x][y] != 0);
        tiles[x][y] = rand.nextInt(10) == 0 ? 4 : 2;
    }

    public void moveLeft()
    {
        boolean needAddTile = false;
        for (int row = 0; row < SIZE; row++)
        {
            int[] newRow = new int[SIZE];
            int i = 0;
            for (int col = 0; col < SIZE; col++)
            {
                if (tiles[row][col] != 0)
                    newRow[i++] = tiles[row][col];
            }
            for (int col = 0; col < SIZE - 1; col++)
            {
                if (newRow[col] != 0 && newRow[col] == newRow[col + 1])
                {
                    newRow[col] *= 2;
                    newRow[col + 1] = 0;
                    if (newRow[col] == WINNING_TILE)
                        won = true;
                }
            }
            int[] finalRow = new int[SIZE];
            i = 0;
            for (int col = 0; col < SIZE; col++)
            {
                if (newRow[col] != 0)
                    finalRow[i++] = newRow[col];
            }
            if (!needAddTile && !arraysEqual(tiles[row], finalRow))
                needAddTile = true;
            tiles[row] = finalRow;
        }
        if (needAddTile)
        {
            addRandomTile();
            if (isGameOver())
                lost = true;
        }
    }

    public void moveRight()
    {
        rotateBoard(2);
        moveLeft();
        rotateBoard(2);
    }

    public void moveDown()
    {
        rotateBoard(1);
        moveLeft();
        rotateBoard(3);
    }

    public void moveUp()
    {
        rotateBoard(3);
        moveLeft();
        rotateBoard(1);
    }

    public void rotateBoard(int times)
    {
        for (int t = 0; t < times; t++)
        {
            int[][] newTiles = new int[SIZE][SIZE];
            for (int row = 0; row < SIZE; row++)
            {
                for (int col = 0; col < SIZE; col++)
                {
                    newTiles[col][SIZE - row - 1] = tiles[row][col];
                }
            }
            tiles = newTiles;
        }
    }

    public boolean arraysEqual(int[] a, int[] b)
    {
        if (a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++)
        {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    public boolean isGameOver()
    {
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                if (tiles[row][col] == 0)
                    return false;
                if (row < SIZE - 1 && tiles[row][col] == tiles[row + 1][col])
                    return false;
                if (col < SIZE - 1 && tiles[row][col] == tiles[row][col + 1])
                    return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(new Color(0xa27bdb));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                drawTile(g, row, col);
            }
        }
    }

    public void drawTile(Graphics g, int row, int col)
    {
        int value = tiles[row][col];
        Image img = getImage(value);
        int x = TILE_MARGIN + col * (TILE_SIZE + TILE_MARGIN);
        int y = TILE_MARGIN + row * (TILE_SIZE + TILE_MARGIN);
        g.drawImage(img, x, y, TILE_SIZE, TILE_SIZE, null);
    }

    public Image getImage(int value)
    {
        String fileName = value + ".png";
        return new ImageIcon(fileName).getImage();
    }
}