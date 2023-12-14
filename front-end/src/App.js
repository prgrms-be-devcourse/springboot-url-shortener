import React, {useState} from "react";
import "./App.css";
import {Button, Card, Form, Input, message, Space, Typography} from "antd";
import axios from "axios";

const {Paragraph} = Typography;

const App = () => {
    const [form] = Form.useForm();
    const longUrl = Form.useWatch("url", form);
    const [shortUrls, setShortUrls] = useState([]);

    const submit = () => {
        axios
            .post("http://localhost:8080/url", {longUrl})
            .then(handlePayload)
            .catch(onFinishFailed);
    };
    const handlePayload = (payload) => {
        if (!shortUrls.includes(payload.data.url)) {
            setShortUrls([payload.data.url, ...shortUrls]);
        }

        message.success("Submit success!");
    };
    const onFinishFailed = () => message.error("Submit failed!");
    const onClear = () => setShortUrls([]);

    return (
        <>
            <Form
                form={form}
                layout="vertical"
                autoComplete="off"
            >
                <Form.Item
                    name="url"
                    label="URL"
                    rules={[
                        {
                            required: true,
                        },
                        {
                            type: "url",
                            warningOnly: true,
                        },
                        {
                            type: "string",
                            min: 6,
                        },
                    ]}
                >
                    <Input placeholder="input placeholder"/>
                </Form.Item>
                <Form.Item>
                    <Space>
                        <Button type="primary" htmlType="submit" onClick={submit}>
                            Submit
                        </Button>
                        <Button htmlType="button" onClick={onClear}>
                            Clear
                        </Button>
                    </Space>
                </Form.Item>
            </Form>
            <Card
                style={{
                    width: 300,
                }}
            >
                {
                    shortUrls.map((shortUrl, idx) => (
                        <Paragraph copyable={{text: shortUrl}} key={idx}>
                            {shortUrl}
                        </Paragraph>
                    ))
                }
            </Card>
        </>
    );
};

export default App;
