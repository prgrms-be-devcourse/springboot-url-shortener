let inputUrl = document.getElementById("originUrl").value;

const urlInput = document.getElementById("originUrl");
urlInput.addEventListener("keyup", () => {
  inputUrl = urlInput.value;
  console.log(inputUrl);
});

const submitBtn = document.getElementById("getUrl");
submitBtn.addEventListener("click", async function changeOriginToShortenUrl(e) {
  const url = `http://localhost:8080/api/v1/urls`;
  const options = {
    method: "POST",
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ originUrl: inputUrl }),
  };
  const res = await fetch(url, options);
  e.preventDefault();
  const data = await res.json();
  if (res.ok) {
    console.log(data.shortUrl);
    urlInput.value = data.shortUrl;
  } else {
    throw Error(data);
  }
});