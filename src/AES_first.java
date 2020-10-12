import java.util.ArrayList;

public class AES_first
{
    private int matrixSize;

    public AES_first()
    {
        this.matrixSize = 4;
    }

    /**
     * Get the matrixSize
     * @return the matrixSize.
     */
    public int getMatrixSize()
    {
        return matrixSize;
    }

    /**
     * aes_first encrypt function.
     * @param plainText-text to encrypt.
     * @param key-key to encrypt with.
     * @return the encrypted text.
     */
    public ArrayList<Byte[][]> aes_first_encrypt(ArrayList<Byte[][]> plainText, Byte[][]key)
    {
        ArrayList<Byte[][]> ciperText;

        ciperText = shiftColumns(plainText, "encrypt");

        ciperText = addRoundKey(ciperText, key);

        return ciperText;
    }

    /**
     * aes_first decrypt function.
     * @param ciperText-text to decrypt.
     * @param key-key to decrypt with.
     * @return the decrypt text.
     */
    public ArrayList<Byte[][]> aes_first_decrypt(ArrayList<Byte[][]> ciperText, Byte[][]key)
    {
        ArrayList<Byte[][]> plainText;

        plainText = addRoundKey(ciperText, key);

        plainText = shiftColumns(plainText, "decrypt");

        return plainText;
    }

    /**
     * Text encrypting/decrypting-the columns shifting.
     * @param inputText-text to shift his columns.
     * @param action-the action to do: encrypt or decrypt.
     * @return text shifted.
     */
    private ArrayList<Byte[][]> shiftColumns(ArrayList<Byte[][]> inputText, String action)
    {
        ArrayList<Byte[][]> outputText = new ArrayList<>();

        for (Byte[][] matrix : inputText)
        {
            outputText.add(matrixShiftColumns(matrix, action));
        }
        return outputText;
    }

    /**
     * Part of the text to encrypt/decrypt-the columns shifting.
     * @param matrix-part of text to shift columns up/down.
     * @param action-the action to do: encrypt or decrypt.
     * @return part of text shifted.
     */
    private Byte[][] matrixShiftColumns(Byte[][] matrix, String action)
    {
        Byte[][] shiftedMatrix = new Byte[matrixSize][matrixSize];

        //col=0 -> no shifting.
        for (int row = 0; row < matrixSize; row++)
        {
            shiftedMatrix[row][0] = matrix[row][0];
        }

        if (action.equals("encrypt"))
        {
            //col=1/2/3 -> shift 1/2/3 up.
            return matrixShiftColumnsUp(matrix, shiftedMatrix);
        }
        //else (action.equals("decrypt"))
        //col=1/2/3 -> shift 1/2/3 down-the reverse shifting.
        return matrixShiftColumnsDown(matrix, shiftedMatrix);
    }

    /**
     * The action is: encrypt.
     * Part of the text to encrypt-to shift columns up.
     * @param matrix-part of text to shift columns up.
     * @param shiftedMatrix-the matrix to shift to-first columns without change.
     * @return part of text shifted.
     */
    private Byte[][] matrixShiftColumnsUp(Byte[][] matrix, Byte[][] shiftedMatrix)
    {
        for (int col = 1; col < matrixSize; col++)
        {
            int matrixCol = 0;
            for (int row = 0; row < matrixSize; row++)
            {
                if (row + col < matrixSize)
                {
                    shiftedMatrix[row][col] = matrix[row + col][col];
                }
                else
                {
                    shiftedMatrix[row][col] = matrix[matrixCol][col];
                    matrixCol++;
                }
            }
        }
        return shiftedMatrix;
    }

    /**
     * The action is: decrypt.
     * Part of the text to decrypt-to shift columns down.
     * @param matrix-part of text to shift columns down.
     * @param shiftedMatrix-the matrix to shift to-first columns without change.
     * @return part of text shifted.
     */
    private Byte[][] matrixShiftColumnsDown(Byte[][] matrix, Byte[][] shiftedMatrix)
    {
        for (int col = 1; col < matrixSize; col++)
        {
            int matrixCol = 3;
            for (int row = matrixSize - 1; row >= 0; row--)
            {
                if (row > col - 1)
                {
                    shiftedMatrix[row][col] = matrix[row - col][col];
                }
                else
                {
                    shiftedMatrix[row][col] = matrix[matrixCol][col];
                    matrixCol--;
                }
            }
        }
        return shiftedMatrix;
    }

    /**
     * Text encrypting/decrypting-the add/remove round key function-using XOR.
     * @param inputText-text to add/remove key to/from.
     * @param key-key to add/remove to/from the text.
     * @return text with/without the key.
     */
    private ArrayList<Byte[][]> addRoundKey(ArrayList<Byte[][]> inputText, Byte[][] key)
    {
        ArrayList<Byte[][]> outputText = new ArrayList<>();

        for (Byte[][] matrix : inputText) {
            outputText.add(matrixAddRoundKey(matrix, key));
        }
        return outputText;
    }

    /**
     * Part of the text to encrypt/decrypt-the add/remove key function-using XOR.
     * @param matrix-part of text to add/remove key to/from.
     * @param key-key to add/remove to/from the part of text.
     * @return part of text with/without key.
     */
    private Byte[][] matrixAddRoundKey(Byte[][] matrix, Byte[][] key)
    {
        Byte[][] xorMatrix = new Byte[matrixSize][matrixSize];

        for (int row = 0; row < matrixSize; row++)
        {
            for (int col = 0; col < matrixSize; col++)
            {
                xorMatrix[row][col] = (byte)(matrix[row][col] ^ key[row][col]);
            }
        }
        return xorMatrix;
    }
}
