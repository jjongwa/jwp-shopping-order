package cart.ui.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.application.repository.MemberRepository;
import cart.domain.member.Member;
import cart.fixture.MemberFixture;
import cart.ui.member.dto.MemberResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
class MemberReadControllerTest {

    @Autowired
    private MemberRepository memberRepository;


    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("GET /members 사용자를 확인한다.")
    void getAllMembersTest() {
        final Member beaver = MemberFixture.비버;
        final Member leo = MemberFixture.레오;
        final Member dino = MemberFixture.디노;

        memberRepository.createMember(beaver);
        memberRepository.createMember(leo);
        memberRepository.createMember(dino);


        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/members")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList(".", MemberResponse.class)).usingRecursiveComparison().ignoringFields("id").isEqualTo(List.of(beaver, leo, dino))
        );
    }

}
