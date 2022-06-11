const copyBtn = document.querySelector(".btn-copy");

copyBtn.addEventListener("click", (event) => {
    const shortUrl = document.getElementById("shortUrl").textContent;

    const textArea = document.createElement("textarea");
    document.body.appendChild(textArea);
    textArea.value = shortUrl;

    textArea.select()
    document.execCommand('copy');

    document.body.removeChild(textArea);

    alert('복사가 완료되었습니다!');
});