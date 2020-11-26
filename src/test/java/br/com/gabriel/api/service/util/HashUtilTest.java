package br.com.gabriel.api.service.util;

import br.com.gabriel.api.util.HashUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class HashUtilTest {

    @Test
    @DisplayName("Generated hash with 64 length")
    public void getSecureHash(){
        String hash = HashUtil.getSecureHash("123");
        assertThat(hash.length()).isEqualTo(64);
    }
}
