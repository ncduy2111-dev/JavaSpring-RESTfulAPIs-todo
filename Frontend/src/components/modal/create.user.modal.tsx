import { App, Modal } from "antd"
import { Input } from 'antd';
import { useState } from "react";
import { createUserApi } from "../../services/api";

interface IProps {
    openCreateModal: boolean;
    setOpenCreateModal: (v: boolean) => void;
    fetchUsers: any;
}
const CreateUserModal = (props: IProps) => {
    const { notification, message } = App.useApp();

    const { openCreateModal, setOpenCreateModal, fetchUsers } = props;
    const [name, setName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);

    const handleSubmit = async () => {
        setLoading(true);
        try {
            const res = await createUserApi(name, email);
            if (res.data.data) {
                message.success("Tạo mới user thành công.");
                setOpenCreateModal(false);
                setName("");
                setEmail("");
                await fetchUsers();
            }
        } catch (error: any) {
            const m = error?.response?.data?.message ?? "unknow";
            notification.error({
                message: "Có lỗi xảy ra",
                description: m
            })
        }


        setLoading(false)
    }

    return (
        <Modal
            title="Create a new user"
            maskClosable={false}
            open={openCreateModal}
            onOk={handleSubmit}
            onCancel={() => {
                setOpenCreateModal(false)
            }}
            okText={"Save"}
            okButtonProps={{
                loading: loading
            }}
        >
            <div style={{ display: "flex", flexDirection: "column", gap: 10, marginBottom: 15 }}>
                <span>Name:</span>
                <Input
                    value={name}
                    onChange={(v) => setName(v.target.value)}
                />
            </div>
            <div style={{ display: "flex", flexDirection: "column", gap: 10 }}>
                <span>Email:</span>
                <Input
                    value={email}
                    onChange={(v) => setEmail(v.target.value)}
                />
            </div>
        </Modal>
    )
}

export default CreateUserModal;