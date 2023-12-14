import React from 'react';
import './App.css';
import { Button, Form, Input, message, Space } from 'antd';
import axios from 'axios';

const App = () => {
  const [form] = Form.useForm();
  const onFinish = () => {
    message.success('Submit success!');
  };
  const submit = () => {
    axios.post('http://localhost:8080/url', {longUrl:'https://www.naver.com'})
    .then(
      data => {
        console.log(data);
      }
    ).catch(
      (e) => {
          console.log(e);
      }
    );
  }
  const onFinishFailed = () => {
    message.error('Submit failed!');
  };
  const onFill = () => {
    form.setFieldsValue({
      url: 'https://taobao.com/',
    });
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
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
            type: 'url',
            warningOnly: true,
          },
          {
            type: 'string',
            min: 6,
          },
        ]}
      >
        <Input placeholder="input placeholder" />
      </Form.Item>
      <Form.Item>
        <Space>
          <Button type="primary" htmlType="submit" onClick={submit}>
            제출
          </Button>
          <Button htmlType="button" onClick={onFill}>
            Fill
          </Button>
        </Space>
      </Form.Item>
    </Form>
  );
};

export default App;
