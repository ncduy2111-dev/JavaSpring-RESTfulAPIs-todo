import { Space, Typography } from 'antd';
const { Text } = Typography;

const HomePage = () => {
    return (
        <div style={{ padding: 20 }}>
            <Space direction="vertical">
                <Text code>Trang chủ</Text>
                <Text code>- Sử dụng React (TypeScript) để làm giao diện</Text>
                <Text code>- Gọi APIs backend đã viết với Spring</Text>
                <Text code>- Hoàn thiện CRUD Users</Text>
            </Space>
        </div>
    )
}

export default HomePage;