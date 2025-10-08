import { App, Input, Modal } from "antd";
import { useEffect, useState } from "react";
import { updateUserApi } from "../../services/api";

interface IProps {
    openUpdateModal: boolean;
    setOpenUpdateModal: (v: boolean) => void;
    fetchUsers: any;
    setDataUpdate: any;
    dataUpdate: {
        id: number;
        name: string;
        email: string
    } | null;
}
const UpdateUserModal = (props: IProps) => {
    const { notification, message } = App.useApp();

    const { openUpdateModal, setOpenUpdateModal,
        fetchUsers, dataUpdate,
        setDataUpdate
    } = props;
    const [name, setName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        if (dataUpdate) {
            setName(dataUpdate.name);
            setEmail(dataUpdate.email);
        }
    }, [dataUpdate])

    const handleSubmit = async () => {
        setLoading(true);
        if (dataUpdate) {
            try {
                const res = await updateUserApi(dataUpdate.id, name, email);
                if (res.data.data) {
                    message.success("Cập nhật user thành công.");
                    setOpenUpdateModal(false);
                    setName("");
                    setEmail("");
                    setDataUpdate(null);
                    await fetchUsers();
                }
            } catch (error: any) {
                const m = error?.response?.data?.message ?? "unknow";
                notification.error({
                    message: "Có lỗi xảy ra",
                    description: m
                })
            }
        }


        setLoading(false)
    }

    return (
        <Modal
            title="Update a user"
            maskClosable={false}
            open={openUpdateModal}
            onOk={handleSubmit}
            onCancel={() => {
                setOpenUpdateModal(false);
                setDataUpdate(null);
            }}
            okText={"Update"}
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

export default UpdateUserModal;