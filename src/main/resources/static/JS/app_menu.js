document.addEventListener("DOMContentLoaded", () => {
  //名前
  const ranking1Name = document.getElementById("js-ranking1Name");
  const ranking2Name = document.getElementById("js-ranking2Name");
  const ranking3Name = document.getElementById("js-ranking3Name");

  //スコア
  const ranking1Score = document.getElementById("js-ranking1Score");
  const ranking2Score = document.getElementById("js-ranking2Score");
  const ranking3Score = document.getElementById("js-ranking3Score");

  const myRanking = document.getElementById("js-myRanking");
  const myScore = document.getElementById("js-myScore");

  //年代リスト
  const listSelect = document.getElementById("js-listSelect");

  //id
  const userId = document.getElementById("js-userId").value;

  var counts = 0;
  var countForAge = 1;

  var selectedText = "";

  //年代を入れる
  fetch("/age_select").then((res) => {
    //RestControllerから受け取った値->res(成功/200 失敗/400)
    if (res.status === 400) {
      console.log("no");
    } else {
      res.json().then((data) => {
        data.forEach((dataEach) => {
          const option1 = document.createElement("option");
          option1.value = dataEach.id;
          option1.text = dataEach.age;
          if (countForAge == 1) {
            selectedText = dataEach.age;
            countForAge = 0;
            pageShow(selectedText);
            myRankingShow(selectedText);
          }
          listSelect.appendChild(option1);
        });
        // セレクト要素のイベントリスナーを追加
        listSelect.addEventListener("change", function () {
          selectedText = this.options[this.selectedIndex].text;
          console.log("選択された年代:", selectedText);
          pageShow(selectedText);
          myRankingShow(selectedText);
        });
      });
    }
  });

  function pageShow(age) {
    //ランキングを入れる
    fetch(`/ranking_select?age=${age}`).then((res) => {
      //RestControllerから受け取った値->res(成功/200 失敗/400)
      if (res.status === 400) {
        console.log("no");
        ranking1Name.textContent = "";
        ranking1Score.textContent = "";
        ranking2Name.textContent = "";
        ranking2Score.textContent = "";
        ranking3Name.textContent = "";
        ranking3Score.textContent = "";
      } else {
        res.json().then((data) => {
          ranking1Name.textContent = "";
          ranking1Score.textContent = "";
          ranking2Name.textContent = "";
          ranking2Score.textContent = "";
          ranking3Name.textContent = "";
          ranking3Score.textContent = "";
          data.forEach((dataEach) => {
            counts++;
            if (counts == 1) {
              ranking1Name.textContent = dataEach.name;
              ranking1Score.textContent = dataEach.gameScore;
            } else if (counts == 2) {
              ranking2Name.textContent = dataEach.name;
              ranking2Score.textContent = dataEach.gameScore;
            } else if (counts == 3) {
              ranking3Name.textContent = dataEach.name;
              ranking3Score.textContent = dataEach.gameScore;
              counts = 0;
            }
          });
        });
      }
    });
  }

  function myRankingShow(age) {
    console.log(userId);
    myRanking.textContent = "-";
    myScore.textContent = 0;
    //自分のランキングを入れる
    fetch(`/myRanking?age=${age}&id=${userId}`).then((res) => {
      //RestControllerから受け取った値->res(成功/200 失敗/400)
      if (res.status === 400) {
        console.log("no");
        myRanking.textContent = "-";
        myScore.textContent = 0;
      } else {
        res.json().then((data) => {
          myRanking.textContent = "-";
          myScore.textContent = 0;
          if(data.rank > 0){
            myRanking.textContent = data.rank;
            myScore.textContent = data.gameScore;
          }

        });
      }
    });
  }
});
