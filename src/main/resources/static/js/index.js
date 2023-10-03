const url = "http://localhost:8080";

// 단축 버튼 클릭시 동작 로직
const shortenUrl = async () => {

    const originalUrl = document.getElementById("url-shortener").value;
    const algorithm = document.getElementById("url-algorithm").value;

    // URL 검증
    if (!isUrl(originalUrl)) {
        alert(`입력하신 값 [${originalUrl}]은 url이 아닙니다.`);
        return;
    }

    // URL 길이 검증
    if (!isInputUrlSizeInRange(originalUrl)) {
        alert(`입력하신 url [${originalUrl}]은 20글자를 초과해야만 합니다.`);
        return;
    }

    // IP 수집 동의
    if (!confirm("URL 단축시 사용자의 IP가 수집됩니다. 동의하시면 확인 동의하지 않으시면 취소를 눌러주세요.")) {
        alert("취소 되었습니다.");
        return;
    }

    const data = {
        originalUrl,
        algorithm
    }

    // fetch로 벡엔드에 데이터 요청하기
    const response = await fetch(`${url}/urls`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data)
    });

    const parseData = await response.json();

    if(parseData.statusCode){
        alert(`[에러코드]:${parseData.statusCode}, [메시지]: ${parseData.message}`);
        initForm();
        return;
    }

    const resultContainer = document.getElementById("result-shortUrl");
    const resultContent = document.getElementById("inputData");
    const resultA = document.getElementById("result-shortUrl-a");

    resultA.href = `${url}/${parseData.shortUrl}`;
    resultContent.innerText = `${url}/${parseData.shortUrl}`;
    resultContainer.style.display = "flex";
}

// 입력 URL 길이 검증
const isInputUrlSizeInRange = (url) => {
    return url.length >= 20;
}

// 입력 URL이 정상적인 URL인지 확인
const isUrl = (url) => {
    const regexp = new RegExp("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    return regexp.test(url);
}

// 결과 값 초기화
const initForm = () => {
    // 받아온 값으로 HTML 태그안에 데이터 삽입
    const resultContainer = document.getElementById("url-info-action");
    resultContainer.style.display = "none";

    const shortenerUrl = document.getElementById("shortener-url");
    const resultOriginalUrl = document.getElementById("original-url");
    const resultCount = document.getElementById("count");
    const resultAlgorithm = document.getElementById("algorithm");
    const resultCreatedAt = document.getElementById("created-at");

    shortenerUrl.value = "";
    resultOriginalUrl.innerText = "";
    resultCount.innerText = "";
    resultAlgorithm.innerText = "";
    resultCreatedAt.innerText = "";
}

const getShortenUrlInfo = async () => {
    const shortenerUrl = document.getElementById("shortener-url").value;

    // fetch로 벡엔드에 데이터 요청하기
    const response = await fetch(`${url}/urls/${shortenerUrl}`);

    const parseData = await response.json();

    if(parseData.statusCode){
        alert(`[에러코드]:${parseData.statusCode}, [메시지]: ${parseData.message}`);
        initForm();
        return;
    }

    inputValueInTag(parseData);
}

const inputValueInTag = (responseData) => {
    // 받아온 값으로 HTML 태그안에 데이터 삽입
    const resultContainer = document.getElementById("url-info-action");
    const shortenerUrl = document.getElementById("shortener-url");
    const resultOriginalUrl = document.getElementById("original-url");
    const resultCount = document.getElementById("count");
    const resultAlgorithm = document.getElementById("algorithm");
    const resultCreatedAt = document.getElementById("created-at");

    shortenerUrl.value = `http://localhost:8080/${responseData.shortUrl}`;
    resultOriginalUrl.innerText = responseData.originalUrl;
    resultCount.innerText = responseData.count;
    resultAlgorithm.innerText = responseData.algorithm;
    resultCreatedAt.innerText = new Date(responseData.createdAt).toLocaleString();

    resultContainer.style.display = "flex";
}
