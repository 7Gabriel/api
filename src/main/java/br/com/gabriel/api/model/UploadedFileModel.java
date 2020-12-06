package br.com.gabriel.api.model;

import br.com.gabriel.api.domain.FileLocation;
import br.com.gabriel.api.domain.UniqueFileName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UploadedFileModel {

    private String name;
    private String location;
}
