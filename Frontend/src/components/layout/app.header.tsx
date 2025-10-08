import { useState } from "react";
import { UserSwitchOutlined, HomeOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Menu } from 'antd';
import { Link } from "react-router";

type MenuItem = Required<MenuProps>['items'][number];

const items: MenuItem[] = [
    {
        label: <Link to={"/"}>Home</Link>,
        key: 'home',
        icon: <HomeOutlined />,
    },
    {
        label: <Link to={"/users"}>Users</Link>,
        key: 'user',
        icon: <UserSwitchOutlined />
    }
];

const AppHeader = () => {
    const [current, setCurrent] = useState('home');

    const onClick: MenuProps['onClick'] = (e) => {
        console.log('click ', e);
        setCurrent(e.key);
    };
    return (
        <Menu
            onClick={onClick}
            selectedKeys={[current]}
            mode="horizontal" items={items}
        />
    )

}

export default AppHeader;
