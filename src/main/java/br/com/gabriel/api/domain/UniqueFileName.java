package br.com.gabriel.api.domain;

import java.util.UUID;

public class UniqueFileName {

    private static final String UNDERSCORE = "_";

    private String fileName;

    public UniqueFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        StringBuilder uniqueFileName = new StringBuilder();
        uniqueFileName.append(UUID.randomUUID());
        uniqueFileName.append(UNDERSCORE);
        uniqueFileName.append(fileName);
        return uniqueFileName.toString();
    }

}
