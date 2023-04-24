describe("MainPage", () => {
  beforeEach(() => {
    cy.visit("http://localhost:5176"); // adjust URL as needed
  });

  beforeEach(() => {
    cy.visit("http://localhost:5176"); // adjust URL as needed
  });

  // 정상 입력했을 경우
  it("정상 입력을 했을 경우", () => {
    cy.get('label[for="peopleNumber"]').type("8");
    cy.get('label[for="gameNumber"]').type("4");
    cy.get('label[for="courtNumber"]').type("2");

    cy.get(".sc-hLseeU").click();
  });

  // 하나의 값을 입력 안했을 경우 3가지
  it("인원수 입력 안했을 경우", () => {
    cy.get("#gameNumber").type("2");
    cy.get("#courtNumber").type("2");

    cy.get(".sc-hLseeU").click();
    cy.contains("값을 입력해주세요.").should("be.visible");
  });

  it("게임수 입력 안했을 경우", () => {
    cy.get("#peopleNumber").type("12");
    cy.get("#courtNumber").type("2");

    cy.get(".sc-hLseeU").click();
    cy.contains("값을 입력해주세요.").should("be.visible");
  });

  it("코트수 입력 안했을 경우", () => {
    cy.visit("localhost:5176");
    cy.get("#peopleNumber").type("12");
    cy.get("#gameNumber").type("2");

    cy.get(".sc-hLseeU").click();
    cy.contains("값을 입력해주세요.").should("be.visible");
  });

  it("최대값 에러 메시지 확인", () => {
    cy.get('label[for="peopleNumber"]').type("51");
    cy.get('label[for="gameNumber"]').type("21");
    cy.get('label[for="courtNumber"]').type("6");
    cy.get(".sc-hLseeU").click();

    cy.contains("최대 값을 확인해주세요.").should("be.visible");
  });

  it("가이드 메시지 확인", () => {
    cy.get('label[for="peopleNumber"]').should(
      "contain.text",
      "매칭을 진행할 인원수를 작성해주세요."
    );
    cy.get('label[for="gameNumber"]').should(
      "contain.text",
      "매칭을 진행할 전체 게임수를 작성해주세요."
    );
    cy.get('label[for="courtNumber"]').should(
      "contain.text",
      "매칭을 진행할 코트수를 작성해주세요."
    );
    cy.get("#peopleNumber").should(
      "have.attr",
      "placeholder",
      "복식 경기를 위해 4명 이상이 필요합니다."
    );
    cy.get("#gameNumber").should(
      "have.attr",
      "placeholder",
      "인원수/4 이상으로 작성해주세요."
    );
    cy.get("#courtNumber").should(
      "have.attr",
      "placeholder",
      "인원수/4 이하로 작성해주세요."
    );
  });

  it("적절한 매칭과 그것에 대한 결과값 테스트", () => {
    cy.intercept("POST", "/users", {
      method: "POST",
      url: "/api/matchs",
      response: {
        gameList: [
          ["Player 1", "Player 2", "Player 3", "Player 4"],
          ["Player 5", "Player 6", "Player 7", "Player 8"],
        ],
      },
    });

    cy.get("#peopleNumber").type("8");
    cy.get("#gameNumber").type("2");
    cy.get("#courtNumber").type("1");
    cy.get(".sc-hLseeU").click();

    cy.contains("Player 1");
    cy.contains("Player 2");
    cy.contains("Player 3");
    cy.contains("Player 4");

    cy.get(".gtwcfK").click();

    cy.contains("Player 5");
    cy.contains("Player 6");
    cy.contains("Player 7");
    cy.contains("Player 8");
  });
});
