package core.constants;

public enum SimpleWord{
        MOTHER("mother", "mottherr"),
        BROTHER("brother", "bbrother");

        private String corrVer;
        private String wrongVer;

        public String corrVer(){return corrVer;}
        public String wrongVer(){return wrongVer;}

        private SimpleWord(String corrVer, String wrongVer){
            this.corrVer = corrVer;
            this.wrongVer = wrongVer;
        }
    }