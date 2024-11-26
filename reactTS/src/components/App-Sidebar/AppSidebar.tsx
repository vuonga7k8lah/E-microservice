import {
    Calendar,
    ChevronUp,
    CopyPlus,
    Home,
    Inbox,
    Rocket,
    Search,
    Settings,
    TableProperties,
    User2,
} from "lucide-react";

import {
    Sidebar,
    SidebarContent,
    SidebarFooter,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
} from "@/components/ui/sidebar";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "@radix-ui/react-dropdown-menu";
import { useState } from "react";
import { logout } from "@/redux/slice/authSlice";

const items = [
    {
        title: "Home",
        url: "#",
        icon: Home,
    },
    {
        title: "Category",
        url: "#",
        icon: Inbox,
        sub: [
            {
                title: "Add Category",
                url: "/categories",
                icon: CopyPlus,
            },
        ],
    },
    {
        title: "Product",
        url: "#",
        icon: Rocket,
        sub: [
            {
                title: "List Product",
                url: "/products",
                icon: TableProperties,
            },
            {
                title: "Add Product",
                url: "/products/add",
                icon: CopyPlus,
            },
        ],
    },
    {
        title: "User",
        url: "users",
        icon: User2,
        sub: [
            {
                title: "Add User",
                url: "/users",
                icon: CopyPlus,
            },
        ],
    },
    {
        title: "Calendar",
        url: "#",
        icon: Calendar,
    },
    {
        title: "Search",
        url: "#",
        icon: Search,
    },
    {
        title: "Settings",
        url: "#",
        icon: Settings,
    },
];
import { useDispatch } from "react-redux";

export function AppSidebar() {
    const [openSubmenu, setOpenSubmenu] = useState(null);
    const dispatch = useDispatch();
    const logoutAction = () => {
        // Logic for logging out, like dispatching an action
        dispatch(logout());
    };

    // @ts-ignore
    const toggleSubmenu = (title) => {
        setOpenSubmenu(openSubmenu === title ? null : title);
    };

    return (
        <Sidebar>
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>Application</SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {items.map((item) => (
                                <SidebarMenuItem key={item.title}>
                                    <SidebarMenuButton asChild>
                                        <a
                                            href={item.url}
                                            onClick={() =>
                                                item.sub &&
                                                toggleSubmenu(item.title)
                                            }
                                        >
                                            <item.icon />
                                            <span>{item.title}</span>
                                            {item.sub && (
                                                <ChevronUp
                                                    className={`ml-auto ${
                                                        openSubmenu ===
                                                        item.title
                                                            ? "rotate-180"
                                                            : ""
                                                    }`}
                                                />
                                            )}
                                        </a>
                                    </SidebarMenuButton>
                                    {item.sub && openSubmenu === item.title && (
                                        <div className="pl-4">
                                            {item.sub.map((subItem) => (
                                                <SidebarMenuItem
                                                    key={subItem.title}
                                                >
                                                    <SidebarMenuButton asChild>
                                                        <a href={subItem.url}>
                                                            <subItem.icon />
                                                            <span>
                                                                {subItem.title}
                                                            </span>
                                                        </a>
                                                    </SidebarMenuButton>
                                                </SidebarMenuItem>
                                            ))}
                                        </div>
                                    )}
                                </SidebarMenuItem>
                            ))}
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
            <SidebarFooter>
                <SidebarMenu>
                    <SidebarMenuItem>
                        <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                                <SidebarMenuButton>
                                    <User2 /> Username
                                    <ChevronUp className="ml-auto" />
                                </SidebarMenuButton>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent
                                side="top"
                                className="w-[--radix-popper-anchor-width]"
                            >
                                <DropdownMenuItem>
                                    <span>Account</span>
                                </DropdownMenuItem>
                                <DropdownMenuItem>
                                    <span>Billing</span>
                                </DropdownMenuItem>
                                <DropdownMenuItem onClick={logoutAction}>
                                    <span>Sign out</span>
                                </DropdownMenuItem>
                            </DropdownMenuContent>
                        </DropdownMenu>
                    </SidebarMenuItem>
                </SidebarMenu>
            </SidebarFooter>
        </Sidebar>
    );
}
