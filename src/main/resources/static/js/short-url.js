const DOMAIN = "http://localhost:8080/"
const URL_API = "api/url/"
document.getElementById('form-shorturl').addEventListener('submit',
    async function (e) {
        e.preventDefault();

        const urlApi = {
            url: e.target.url.value
        };

        try {
            const response = await fetch('/url', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(urlApi)
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result)
                document.getElementById('result').value = DOMAIN + URL_API+ result.shortUrl;
            } else {
                console.log("error")
            }
        } catch(e) {
            console.log(e);
        }
    });


document.getElementById('shortUrl-copy').addEventListener('click',
    async function (e) {
        e.preventDefault();

        const copyData = document.getElementById('result').value;
        console.log(copyData)
        window.navigator.clipboard.writeText(copyData).then(()=>{
            document.getElementById('copy-message').textContent = "복사되었습니다!";
        })
})