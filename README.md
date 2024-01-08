# 2023_OOP_TeamProject
## 팀원
### 2020125054 이호경
### 2020125074 황규진 (팀장)
### 2022125069 황주원
***
#### How to use git
  * apt update
  * apt install git

#### 주요 git 명령어   
* add : 파일을 추적 대상으로 포함시킬 때, 또는 커밋 대상으로 포함킬 때 사용
    + git add <file name>   
* commit
* git reset HEAD <file> : stage된 파일을 unstaged로 변경
* git checkout -- <file> : stage되어 있는 파일을 수정한 후 수정 전으로 되돌림 
* branch
* status
***
##### git 협업 순서 (명령어는 안드로이드 스튜디오 터미널에서 실행)
1. 최근 업데이트 내용 다운 : git pull (main 브랜치)
2. 브랜치 생성 : github 사이트에서 브랜치 생성
3. 브랜치 업데이트 : git fetch -p
4. 브랜치 이동 : git checkout <브랜치 이름>
5. 안드로이드 스튜디오에서 코드 수정
6. 수정된 코드 부분 추가할 수 있는 상태로 만들기 : git add .
7. 수정된 코드 커밋 하기 : git commit -m "Meaning name"
8. 수정된 코드 업로드하기 : git push
<br>cf) git commit -am "Meaning name" / am = add + commit

