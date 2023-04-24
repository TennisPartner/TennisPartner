describe("정상 등록 했을 경우 (12,4,2)", () => {
  it("정상 등록 했을 경우", () => {
    cy.visit("localhost:5176");
    cy.get("#peopleNumber").type("12");
    cy.get("#gameNumber").type("4");
    cy.get("#courtNumber").type("2");

    cy.get(".sc-hLseeU").click();
  });

  it("인원수 입력 안했을 경우", () => {
    cy.visit("localhost:5176");
    cy.get("#gameNumber").type("2");
    cy.get("#courtNumber").type("2");

    cy.get(".sc-hLseeU").click();
  });

  it("게임수 입력 안했을 경우", () => {
    cy.visit("localhost:5176");
    cy.get("#peopleNumber").type("12");
    cy.get("#courtNumber").type("2");

    cy.get(".sc-hLseeU").click();
  });

  it("코트수 입력 안했을 경우", () => {
    cy.visit("localhost:5176");
    cy.get("#peopleNumber").type("12");
    cy.get("#gameNumber").type("2");

    cy.get(".sc-hLseeU").click();
  });
});
