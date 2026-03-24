public class CaesarCipher {

    // Encrypt method
    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encrypted = (char) ((ch - base + key) % 26 + base);
                result.append(encrypted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decrypt method (optional)
    public static String decrypt(String text, int key) {
        return encrypt(text, 26 - key);
    }
}