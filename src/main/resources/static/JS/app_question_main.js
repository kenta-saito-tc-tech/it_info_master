document.addEventListener("DOMContentLoaded", () => {
    //年代リスト
    const listSelect = document.getElementById("js-listSelect");
    //id
    const userId = document.getElementById("js-userId").value;

    //グラフ
    const graph_databaseSpace = document.getElementById("js-graph_databaseSpace");
    const graph_database = document.getElementById("js-graph_database");
    const graph_systemSpace = document.getElementById("js-graph_systemSpace");
    const graph_system = document.getElementById("js-graph_system");
    const graph_infomationSpace = document.getElementById("js-graph_infomationSpace");
    const graph_infomation = document.getElementById("js-graph_infomation");
    const graph_networkSpace = document.getElementById("js-graph_networkSpace");
    const graph_network = document.getElementById("js-graph_network");
    const graph_managementSpace = document.getElementById("js-graph_managementSpace");
    const graph_management = document.getElementById("js-graph_management");
    const graph_securitySpace = document.getElementById("js-graph_securitySpace");
    const graph_security = document.getElementById("js-graph_security");

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
            }
            listSelect.appendChild(option1);
          });
          // セレクト要素のイベントリスナーを追加
          listSelect.addEventListener("change", function () {
            selectedText = this.options[this.selectedIndex].text;
            console.log("選択された年代:", selectedText);
            pageShow(selectedText);
          });
        });
      }
    });


    //仮データ
    // const persent = 80;
    // graph_databaseSpace.style.width = persent + '%';
    // graph_database.textContent = persent + '%';
    // graph_systemSpace.style.width = '50%';
    // graph_system.textContent = '50%';
    // graph_infomationSpace.style.width = '40%';
    // graph_infomation.textContent = '40%';
    // graph_networkSpace.style.width = '20%';
    // graph_network.textContent = '20%';
    // graph_managementSpace.style.width = '40%';
    // graph_management.textContent = '40%';
    // graph_securitySpace.style.width = '60%';
    // graph_security.textContent = '60%';

  
    function pageShow(age) {
      //ランキングを入れる
      fetch(`/graph?age=${age}`).then((res) => {
        //RestControllerから受け取った値->res(成功/200 失敗/400)
        if (res.status === 400) {
          console.log("no");
        } else {
          res.json().then((data) => {
            data.forEach((dataEach) => {
                if(dataEach.categoryName == 'データベース'){
                    graph_databaseSpace.style.width = dataEach.percentage + '%';
                    graph_database.textContent = dataEach.percentage + '%';
                }else if(dataEach.categoryName == 'システム構成要素'){
                    graph_systemSpace.style.width = dataEach.percentage + '%';
                    graph_system.textContent = dataEach.percentage + '%';
                }else if(dataEach.categoryName == '情報処理'){
                    graph_infomationSpace.style.width = dataEach.percentage + '%';
                    graph_infomation.textContent = dataEach.percentage + '%';
                }else if(dataEach.categoryName == 'ネットワーク'){
                    graph_networkSpace.style.width = dataEach.percentage + '%';
                    graph_network.textContent = dataEach.percentage + '%';
                }else if(dataEach.categoryName == 'マネジメント'){
                    graph_managementSpace.style.width = dataEach.percentage + '%';
                    graph_management.textContent = dataEach.percentage + '%';
                }else if(dataEach.categoryName == 'セキュリティ'){
                    graph_securitySpace.style.width = dataEach.percentage + '%';
                    graph_security.textContent = dataEach.percentage + '%';
                }
            });
          });
        }
      });
    }

  });
  