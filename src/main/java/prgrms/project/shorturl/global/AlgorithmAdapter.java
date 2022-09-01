package prgrms.project.shorturl.global;

public enum AlgorithmAdapter {

    BASE62() {
        private final static int IDX = 62;

        private static final char[] BASE62 = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        @Override
        public String encode(Object value) {
            var id = (Long) value;
            var encodedUrl = new StringBuilder();

            while (id > 0) {
                encodedUrl.append(BASE62[(int) (id % IDX)]);
                id /= IDX;
            }

            return encodedUrl.reverse().toString();
        }
    },

    FAKE62() {
        private final static int IDX = 62;

        private static final char[] FAKE62 = "abcdefghijkl$^&*()!mnopqrstuvwxyz12345NOPQRSTUVWXYZ67890ABCDEFGHIJKLM".toCharArray();

        @Override
        public String encode(Object value) {
            var id = (Long) value;
            var encodedUrl = new StringBuilder();

            while (id > 0) {
                encodedUrl.append(FAKE62[(int) (id % IDX)]);
                id /= 62;
            }

            return encodedUrl.reverse().toString();
        }
    };

    public abstract String encode(Object value);
}
